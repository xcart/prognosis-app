export default function getContinuousColorCode(value) {
  if (value < 1) {
    return '#eee';
  }
  const absoluteMaxWorkloadMinutes = 8 * 60;

  let hsl = transitionOfHueRange(value / absoluteMaxWorkloadMinutes, 180, 0);
  return `hsl(${hsl.hue}deg ${hsl.saturation}% ${hsl.lightness}%)`;
}

function transitionOfHueRange(percentage, startHue, endHue) {
  // From 'startHue' 'percentage'-many to 'endHue'
  let hue = (percentage * (endHue - startHue)) + startHue;

  return {hue, saturation: 65.0, lightness: 55.0};
}