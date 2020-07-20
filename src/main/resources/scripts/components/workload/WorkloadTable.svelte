<script>
  import getDates from "../../util/dates.js"
  export let report = null
  let calendarLength = 30
  let dates = getDates(new Date(), new Date().addDays(calendarLength - 1))
  let formatWorkload = (value) => (value / 60.0).toFixed(1)
  let limitValues = (values) => values.slice(0, calendarLength)

  let transitionOfHueRange = (percentage, startHue, endHue) => {
    // From 'startHue' 'percentage'-many to 'endHue'
    // Finally map from [0°, 360°] -> [0, 1.0] by dividing
    let hue = ((percentage * (endHue - startHue)) + startHue);

    const saturation = 65.0;
    const lightness = 55.0;

    // Get the color
    return {hue, saturation, lightness};
  }

  let continuousColorCode = (value) => {
    if (value < 1) {
      return '#eee';
    }
    const absoluteMaxWorkloadMinutes = 8 * 60;

    let hsl = transitionOfHueRange(value / absoluteMaxWorkloadMinutes, 180, 0);
    return `hsl(${hsl.hue}deg ${hsl.saturation}% ${hsl.lightness}%)`;
  }
</script>

<div class="calendar-section">
  <div class="table-header">
    {#each dates as date}
      <div class="date-column">
        <span>{date.getUTCDate()}</span>
      </div>
    {/each}
  </div>
  <div class="table-body">
    {#each report as reportLine}
      <div class="table-row">
        {#each limitValues(reportLine.items) as item}
          <div class="data-column" style="background: {continuousColorCode(item.workload)}">
            <span class="workload-value ">{formatWorkload(item.workload)}</span>
            <div class="extra-info">
              {#each item.issues as issue}
                <a class="issue-link" href="https://xcart.myjetbrains.com/youtrack/issue/{issue.idReadable}" target="_blank">{issue.idReadable}</a>
              {/each}
            </div>
          </div>
        {/each}
      </div>
    {/each}
  </div>
</div>

<style>
.table-header {
  display: flex;
}

.table-row {
  display: flex;
  height: 2rem;
  min-height: 2rem;
  align-items: center;
}

.table-header + .table-body {
  margin-top: 1rem;
}

.calendar-section .table-row, .date-column {
  text-align: center;
}

.date-column, .data-column {
  width: 2rem;
  display: flex;
  height: 100%;
  justify-content: center;
  flex-direction: column;
  position: relative;
}

.data-column.color-red {
  background: #d55;
}

.data-column.color-yellow {
  background: #dd5;
}

.data-column.color-green {
  background: #5d5;
}

.data-column.color-pale {
  background: #cfc;
}

.data-column.color-white {
  background: #eee;
}

.data-column .extra-info {
  display: none;
  position: absolute;
  top: 100%;
  left: 50%;
  transform: translate(-50%, 0);
  box-shadow: 0 1px 5px 0px #ccc;
  background: white;
  padding: .5rem;
  z-index: 1;
}

.data-column:hover .extra-info {
  display: block;
}

.extra-info .issue-link {
  display: block;
    white-space: nowrap;
}

.data-column .workload-value {
  opacity: .2;
}

.data-column:hover .workload-value {
  opacity: .9;
}
</style>