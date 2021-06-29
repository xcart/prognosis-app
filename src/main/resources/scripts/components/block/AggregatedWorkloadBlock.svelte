<script>
  import {getContinuousColorCode} from "../../util/colorCode"
  import {tooltip} from "../../actions/tooltip";
  import WorkloadItemTooltip from "../tooltip/WorkloadItemTooltip.svelte"
  import DayOffTooltip from "../tooltip/DayOffTooltip.svelte"
  import RowContainer from "../table/RowContainer.svelte"

  export let swimlane = []

  let formatWorkload = (value) => (value / 60.0).toFixed(1)
  let getCellStyle = (item) => item.workload > 0 ? 'background: ' + getContinuousColorCode(item.workload) + ';' : ''
  let tooltipParams = (item) => {
    if (item.type === "WorkingDay" && item.issues && item.issues.length > 0) {
      return {component: WorkloadItemTooltip, props: {issues: item.issues}, interactive: true}
    } else if (item.type !== "WorkingDay") {
      return {component: DayOffTooltip, props: {type: item.type}}
    }
    return null
  }

  let getItemClass = (item) => {
    return item.type + " " + item.phase
  }
</script>

<RowContainer className="aggregated-workload-block">
    {#each swimlane as item}
        <div class="data-column {getItemClass(item)}"
             style="{getCellStyle(item)}"
             use:tooltip={tooltipParams(item)}>
            <span class="workload-value ">{formatWorkload(item.workload)}</span>
        </div>
    {/each}
    <slot></slot>
</RowContainer>

<style>
    .data-column {
        width: var(--table-cell-width);
        min-width: var(--table-cell-width);
        max-width: var(--table-cell-width);
        display: inline-flex;
        height: 100%;
        justify-content: center;
        flex-direction: column;
        position: relative;
        text-align: center;
        background: #eee;
    }

    .data-column.Weekend .workload-value,
    .data-column.Vacation .workload-value,
    .data-column.Holiday .workload-value {
        opacity: 0;
    }

    .data-column:not(.WorkingDay) + .data-column.WorkingDay {
        border-left: 1px solid #ccc;
    }

    .data-column:not(.WorkingDay) + .data-column.WorkingDay,
    .data-column.WorkingDay + .data-column:not(.WorkingDay) {
        border-left: 1px solid #ccc;
    }

    .data-column.WorkingDay:last-child {
        border-right: 1px solid #ccc;
    }

    .data-column.WorkingDay {
        background: #fff;
        border-top: 1px solid #ccc;
        border-bottom: 1px solid #ccc;
    }

    .data-column.VerificationDay {
        position: relative;
        border-right: 2px solid var(--verification-day);
    }

    .data-column.VerificationDay::after {
        position: absolute;
        right: -7px;
        z-index: 1;
        top: -1px;
        content: "";
        width: 0;
        height: 0;
        border-left: 6px solid transparent;
        border-right: 6px solid transparent;
        border-top: 6px solid var(--verification-day);
    }

    .data-column.WorkingDay.Testing {
        opacity: .7;
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

    .data-column.empty .workload-value {
        opacity: 0;
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
    }

    .data-column.WorkingDay:hover .workload-value {
        opacity: 1;
    }
</style>