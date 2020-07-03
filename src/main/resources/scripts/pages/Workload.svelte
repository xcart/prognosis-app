<script>
  import getDates from "../util/dates.js"
  export let state = null
  let calendarLength = 30
  let report = Object.values(state.report)
  let dates = getDates(new Date(), new Date().addDays(calendarLength - 1))
  let formatWorkload = (value) => (value / 60.0).toFixed(1)
  let limitValues = (values) => values.slice(0, calendarLength)
  let filterReport = (report) => report.filter((line) => line.items.length > 0)
  let colorCode = (value) => {
    if (value > 360) {
      return "red"
    } else if (value > 180) {
      return "yellow"
    } else if (value > 30) {
      return "green"
    } else if (value > 1) {
      return "pale"
    } else {
      return "white"
    }
  }
</script>

<section class="page">
  <h1>Workload analysis</h1>
  <div class="workload-table">
    <div class="user-section">
      <div class="table-header">
        <span>-------</span>
      </div>
      <div class="table-body">
        {#each filterReport(report) as reportLine}
          <div class="table-row">
            <div class="user-column">
              <span>{reportLine.user.fullName}</span>
            </div>
          </div>
        {/each}
      </div>
    </div>
    <div class="calendar-section">
      <div class="table-header">
        {#each dates as date}
          <div class="date-column">
            <span>{date.getUTCDate()}</span>
          </div>
        {/each}
      </div>
      <div class="table-body">
        {#each filterReport(report) as reportLine}
          <div class="table-row">
            {#each limitValues(reportLine.items) as item}
              <div class="data-column color-{colorCode(item.workload)}">
                <span class="workload-value ">{formatWorkload(item.workload)}</span>
              </div>
            {/each}
          </div>
        {/each}
      </div>
    </div>
  </div>
</section>

<style>
.workload-table {
  display: flex;
}

.table-header {
  display: flex;
}

.table-row {
  display: flex;
  height: 2rem;
  min-height: 2rem;
  align-items: center;
}

.user-section .table-row {
  justify-content: flex-end;
}

.table-header + .table-body {
  margin-top: 1rem;
}

.user-section + .calendar-section {
  margin-left: 1rem;
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

.data-column .workload-value {
  opacity: .2;
}

.data-column:hover .workload-value {
  opacity: .9;
}

</style>
