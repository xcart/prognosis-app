<script>
    import getContinuousColorCode from "../../../util/colorCode"

    export let swimlane = null

    let formatWorkload = (value) => (value / 60.0).toFixed(1)
</script>

<div class="table-row user-swimlane">
  {#each swimlane as item}
      <div class="data-column"
           style="background: {getContinuousColorCode(item.workload)}">
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
    }

    .data-column .workload-value {
        opacity: .2;
        cursor: default;
    }

    .data-column:hover .workload-value {
        opacity: .9;
    }
</style>