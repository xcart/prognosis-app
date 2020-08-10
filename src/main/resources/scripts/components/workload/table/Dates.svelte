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

    let getDayClass = function (date) {
        let day = date.getDay()
        switch(day) {
            case 0: return 'sunday'
            case 1: return 'monday'
            case 2: return 'tuesday'
            case 3: return 'wednesday'
            case 4: return 'thursday'
            case 5: return 'friday'
            case 6: return 'saturday'
        }
    }
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
      <div class="date-column {getDayClass(date)}">
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
        text-align: center;
    }

    .month-column {
        width: calc(var(--table-row-width) * var(--data-size));
        min-width: calc(var(--table-row-width) * var(--data-size));
        max-width: calc(var(--table-row-width) * var(--data-size));
        text-align: left;
        border-left: 1px solid #eee;
        padding-left: .7rem;
    }

    .month-column span {
        margin-left: -1px;
    }

    .date-column, .month-column {
        display: flex;
        height: 100%;
        justify-content: center;
        flex-direction: column;
        position: relative;
    }

    :global(.date-column.monday) {
        border-left: 1px solid #eee;
    }

    :global(.date-column.monday span) {
        margin-left: -1px;
    }
</style>
