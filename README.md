# Mandelbrot
## Rendering of the Mandelbrot and Julia sets.
  
## TODO:
Add example outputs

Create setup/run/install script

Document Processing Usage

### Notes:
Controls are janky but powerful

Run with -Xmx8G or other memory size when dealing with large images (resolution <1)

Must have processing-core.jar added to classpath

Pass output file.txt in cmd to load from that save

## Controls

click to render centered at that point

'ctrl' switches between mandelbrot and julia sets, starts julia set from render point in mandelbrot set

' ' renders

= saves image (after re-rendering with new settings)


q/a zooms

w/s change the iterations calculated (increases/decreases accuracy)

r/f change resolution (pixel size)

e/d change rate of change


1/z change hue

2/x change saturation

3/c change brightness

dont press p
