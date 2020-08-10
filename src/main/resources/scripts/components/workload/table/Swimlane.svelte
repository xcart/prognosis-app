<script lang="ts">
    import getContinuousColorCode from "../../../util/colorCode";
    import type {DailyWorkloadItem} from "../../../types"

    export let swimlane: Array<DailyWorkloadItem> = null

    let formatWorkload = (value) => (value / 60.0).toFixed(1)
</script>

<div class="table-row user-swimlane">
  {#each swimlane as item}
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

<style>
    .table-row {
        display: flex;
        height: 2rem;
        min-height: 2rem;
        align-items: center;
    }

    .table-row + .table-row {
        margin-top: var(--table-line-margin);
    }

    .data-column {
        width: var(--table-row-width);
        min-width: var(--table-row-width);
        max-width: var(--table-row-width);
        display: flex;
        height: 100%;
        justify-content: center;
        flex-direction: column;
        position: relative;
        text-align: center;
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