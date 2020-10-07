export function getContinuousColorCode(value) {
  if (value < 1) {
    return '#eee';
  }
  const absoluteMaxWorkloadMinutes = 8 * 60;
  const clampLimit = 1.1;

  let hsl = transitionOfHueRange(Math.min(value / absoluteMaxWorkloadMinutes, clampLimit), 180, 0);
  return `hsl(${hsl.hue}deg ${hsl.saturation}% ${hsl.lightness}%)`;
}

export function getProjectCountColorCode(value) {
  if (value < 1) {
    return '#eee';
  }
  const maxProjects = 8;
  const clampLimit = 1.1;

  let hsl = transitionOfHueRange(Math.min(value / maxProjects, clampLimit), 180, 0);
  return `hsl(${hsl.hue}deg ${hsl.saturation}% ${hsl.lightness}%)`;
}

function transitionOfHueRange(percentage, startHue, endHue) {
  // From 'startHue' 'percentage'-many to 'endHue'
  let hue = (percentage * (endHue - startHue)) + startHue;

  return {hue, saturation: 60.0, lightness: 60.0};
}