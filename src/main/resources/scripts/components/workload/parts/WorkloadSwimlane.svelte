<script>
    import {getContinuousColorCode} from "../../../util/colorCode"
    import {tooltip} from "../../../actions/tooltip";
    import IssueListTooltip from "./IssueListTooltip.svelte"

    export let swimlane = null

    let formatWorkload = (value) => (value / 60.0).toFixed(1)
    let hasIssues = (issues) => issues && issues.length > 0
    let getItemClass = (item) => item.type
</script>

<div class="table-row user-swimlane">
  {#each swimlane as item}
      <div class="data-column {getItemClass(item)}"
           style="{item.workload > 0 ? 'background: ' + getContinuousColorCode(item.workload) + ';' : ''}"
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
        background: #eee;
    }

    .data-column.Weekend .workload-value,
    .data-column.Vacation .workload-value,
    .data-column.Holiday .workload-value
    {
        opacity: 0;
    }

    .data-column.WorkingDay {
        background: #fff;
    }

    .data-column.Vacation {
        opacity: .5;
        background: repeating-linear-gradient(
          45deg,
          #bebebe 1px,
          #bebebe 7px,
          #969696 7px,
          #969696 12px
        );
    }

    .data-column.Holiday {
        opacity: .5;
        background: repeating-linear-gradient(
          45deg,
          #BE8F8F 1px,
          #BE8F8F 7px,
          #e7a1a1 7px,
          #e7a1a1 12px
        );
    }

    .data-column .workload-value {
        opacity: .4;
        cursor: default;
    }

    .data-column.WorkingDay:hover .workload-value {
        opacity: 1;
    }
</style>