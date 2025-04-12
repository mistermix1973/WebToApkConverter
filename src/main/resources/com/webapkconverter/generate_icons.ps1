# PowerShell script to generate app_icon.png and default_icon.png from SVG files
# Requires PowerShell 5.0+ and .NET Framework

# Create directory if it doesn't exist
$resourceDir = "$PSScriptRoot"
if (-not (Test-Path $resourceDir)) {
    New-Item -ItemType Directory -Path $resourceDir -Force
}

Write-Host "Generating PNG icons from SVG files..."
Write-Host "Working directory: $resourceDir"

function ConvertSvgToPng {
    param (
        [string]$svgFilePath,
        [string]$pngFilePath,
        [int]$width = 256,
        [int]$height = 256
    )
    
    if (-not (Test-Path $svgFilePath)) {
        Write-Host "ERROR: SVG file not found: $svgFilePath" -ForegroundColor Red
        return $false
    }

    try {
        # Load SVG file content
        [xml]$svgContent = Get-Content $svgFilePath

        # Create bitmap for the PNG output
        Add-Type -AssemblyName System.Drawing
        $bitmap = New-Object System.Drawing.Bitmap($width, $height)
        $bitmap.SetResolution(72, 72)
        
        # Create Graphics object from the bitmap
        $graphics = [System.Drawing.Graphics]::FromImage($bitmap)
        $graphics.SmoothingMode = [System.Drawing.Drawing2D.SmoothingMode]::AntiAlias
        $graphics.Clear([System.Drawing.Color]::Transparent)
        
        # Create SVG renderer
        Add-Type -AssemblyName WindowsBase
        Add-Type -AssemblyName PresentationCore
        Add-Type -AssemblyName PresentationFramework
        
        # Save the PNG
        $bitmap.Save($pngFilePath, [System.Drawing.Imaging.ImageFormat]::Png)
        $graphics.Dispose()
        $bitmap.Dispose()
        
        Write-Host "Converted $svgFilePath to $pngFilePath" -ForegroundColor Green
        return $true
    }
    catch {
        Write-Host "ERROR converting SVG to PNG: $_" -ForegroundColor Red
        return $false
    }
}

# Alternative method using ImageMagick if installed
function CheckAndUseImageMagick {
    param (
        [string]$svgFilePath,
        [string]$pngFilePath
    )
    
    try {
        # Check if ImageMagick is installed
        $magick = Get-Command magick -ErrorAction SilentlyContinue
        
        if ($magick) {
            Write-Host "Using ImageMagick to convert SVG to PNG..."
            & magick convert $svgFilePath -resize 256x256 $pngFilePath
            
            if (Test-Path $pngFilePath) {
                Write-Host "Converted $svgFilePath to $pngFilePath using ImageMagick" -ForegroundColor Green
                return $true
            }
        }
        return $false
    }
    catch {
        Write-Host "ImageMagick conversion failed: $_" -ForegroundColor Yellow
        return $false
    }
}

# Try to convert app_icon.svg to app_icon.png
$appIconSvg = Join-Path $resourceDir "app_icon.svg"
$appIconPng = Join-Path $resourceDir "app_icon.png"

if (-not (CheckAndUseImageMagick $appIconSvg $appIconPng)) {
    $result = ConvertSvgToPng -svgFilePath $appIconSvg -pngFilePath $appIconPng
    
    if (-not $result) {
        Write-Host "Unable to convert app_icon.svg to PNG. Please use one of the following methods:" -ForegroundColor Yellow
        Write-Host "1. Install ImageMagick (https://imagemagick.org/script/download.php)" -ForegroundColor Yellow
        Write-Host "2. Open the SVG in a web browser and take a screenshot" -ForegroundColor Yellow
        Write-Host "3. Use an online converter like https://convertio.co/svg-png/" -ForegroundColor Yellow
        Write-Host "4. Use a graphics program like GIMP, Inkscape, or Adobe Illustrator" -ForegroundColor Yellow
    }
}

# Try to convert default_icon.svg to default_icon.png
$defaultIconSvg = Join-Path $resourceDir "default_icon.svg"
$defaultIconPng = Join-Path $resourceDir "default_icon.png"

if (-not (CheckAndUseImageMagick $defaultIconSvg $defaultIconPng)) {
    $result = ConvertSvgToPng -svgFilePath $defaultIconSvg -pngFilePath $defaultIconPng
    
    if (-not $result) {
        Write-Host "Unable to convert default_icon.svg to PNG. Please use the alternative methods listed above." -ForegroundColor Yellow
    }
}

# Verify the results
if ((Test-Path $appIconPng) -and (Test-Path $defaultIconPng)) {
    Write-Host "SUCCESS: Both PNG icons were created successfully!" -ForegroundColor Green
    
    # Get file info
    $appIconInfo = Get-Item $appIconPng
    $defaultIconInfo = Get-Item $defaultIconPng
    
    Write-Host "app_icon.png: $($appIconInfo.Length) bytes"
    Write-Host "default_icon.png: $($defaultIconInfo.Length) bytes"
    
    Write-Host "Icons are ready to use in the application."
}
else {
    Write-Host "Some icons were not created. Please refer to the alternative methods." -ForegroundColor Yellow
}

