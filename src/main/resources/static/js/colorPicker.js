var colorPicker = new iro.ColorPicker(".picker", {
    // color picker options
    // Option guide: https://iro.js.org/guide.html#color-picker-options
    width: 150,
    type: "ChromeDevToolColorPicker",
    color: "rgb(255, 0, 0)",
  });

  colorPicker.on('color:change', function(color) {
    // log the current color as a HEX string
    console.log(color.hexString);
    document.querySelector(".preview").style.backgroundColor = color.hexString;
  });