<script>
    import getDates from "../../../util/dates.js"

    export let duration = 30

    let getMonthName = function (date) {
        const options = {month: 'long'};
        return new Intl.DateTimeFormat('en-US', options).format(date);
    }
    let dates = getDates(new Date(), new Date().addDays(duration - 1))
    let months = Array.from(dates.reduce((acc, item) => {
        let month = item.getUTCMonth() + 1
        let label = getMonthName(item)
        let object = {
            number: month,
            label: label,
            size: 1
        }
        if (acc.has(month)) {
            let existing = acc.get(month)
            object.size += existing.size
        }

        acc.set(month, object)
        return acc
    }, new Map()).values())
</script>

<div class="table-row">
  {#each months as month}
      <div class="month-column" style="--data-size: {month.size}" data-month="{month.number}">
          <span>{month.label}</span>
      </div>
  {/each}
</div>
<div class="table-row">
  {#each dates as date}
      <div class="date-column">
          <span>{date.getUTCDate()}</span>
      </div>
  {/each}
</div>

<style>
    .table-row {
        display: flex;
        height: var(--table-row-height);
        min-height: var(--table-row-height);
        align-items: center;
    }

    .date-column {
        width: var(--table-row-width);
        min-width: var(--table-row-width);
        max-width: var(--table-row-width);
    }

    .month-column {
        width: calc(var(--table-row-width) * var(--data-size));
        min-width: calc(var(--table-row-width) * var(--data-size));
        max-width: calc(var(--table-row-width) * var(--data-size));
    }

    .date-column, .month-column {
        display: flex;
        height: 100%;
        justify-content: center;
        flex-direction: column;
        position: relative;
        text-align: center;
    }
</style>
