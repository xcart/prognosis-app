<script>
    import {getContinuousColorCode} from "../../util/colorCode"
    import {tooltip} from "../../actions/tooltip";
    import WorkloadItemTooltip from "./WorkloadItemTooltip.svelte"

    export let swimlane = null

    let formatWorkload = (value) => (value / 60.0).toFixed(1)
    let getCellStyle = (item) => item.workload > 0 ? 'background: ' + getContinuousColorCode(item.workload) + ';' : ''
    let tooltipParams = (item) => {
      item.issues && item.issues.length > 0
        ? {component: WorkloadItemTooltip, props: {issues: item.issues}, interactive: true}
        : null
    }
</script>

<div class="table-row user-swimlane">
  {#each swimlane as item}
      <div class="data-column {item.type}"
           style="{getCellStyle(item)}"
           use:tooltip={tooltipParams(item)}>
          <span class="workload-value ">{formatWorkload(item.workload)}</span>
      </div>
  {/each}
</div>

<style>
    .table-row {
        display: flex;
        height: var(--table-extended-row-height);
        height: var(--table-extended-row-height);
        padding: var(--table-extended-row-v-padding);
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