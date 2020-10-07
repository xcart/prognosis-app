<script>
    import {getContinuousColorCode} from "../../../util/colorCode"
    import {tooltip} from "../../../actions/tooltip";
    import IssueListTooltip from "./IssueListTooltip.svelte"

    export let swimlane = null

    let formatWorkload = (value) => (value / 60.0).toFixed(1)
    let hasIssues = (issues) => issues && issues.length > 0
</script>

<div class="table-row user-swimlane">
  {#each swimlane as item}
      <div class="data-column"
           style="background: {getContinuousColorCode(item.workload)}"
           use:tooltip={{component: IssueListTooltip, props: {issues: item.issues}, interactive: true, display: hasIssues(item.issues) }}>
          <span class="workload-value ">{formatWorkload(item.workload)}</span>
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

    .data-column .workload-value {
        opacity: .2;
        cursor: default;
    }

    .data-column:hover .workload-value {
        opacity: .9;
    }
</style>