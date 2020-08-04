<script>
    import getContinuousColorCode from "../../util/colorCode";
    import DateTableHeader from "./table/DateTableHeader.svelte"

    export let report = null
    let calendarLength = 30
    let formatWorkload = (value) => (value / 60.0).toFixed(1)
    let limitValues = (values) => values.slice(0, calendarLength)
</script>

<div class="calendar-section">
    <DateTableHeader duration={calendarLength} />
    <div class="table-body">
      {#each report as reportLine}
          <div class="table-row">
            {#each limitValues(reportLine.items) as item}
                <div class="data-column" style="background: {getContinuousColorCode(item.workload)}">
                    <span class="workload-value ">{formatWorkload(item.workload)}</span>
                    <div class="extra-info">
                      {#each item.issues as issue}
                          <a class="issue-link" href="https://xcart.myjetbrains.com/youtrack/issue/{issue.idReadable}"
                             target="_blank">{issue.idReadable}</a>
                      {/each}
                    </div>
                </div>
            {/each}
          </div>
      {/each}
    </div>
</div>

<style>
    :global(.table-header) {
        display: flex;
    }

    .table-row {
        display: flex;
        height: 2rem;
        min-height: 2rem;
        align-items: center;
    }

    :global(.table-header + .table-body) {
        margin-top: 1rem;
    }

    :global(.calendar-section .table-row), :global(.date-column) {
        text-align: center;
    }

    :global(.date-column), :global(.data-column) {
        width: 2rem;
        display: flex;
        height: 100%;
        justify-content: center;
        flex-direction: column;
        position: relative;
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