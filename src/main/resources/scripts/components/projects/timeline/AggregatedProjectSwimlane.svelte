<script>
  import {getProjectCountColorCode} from "../../../util/colorCode"
  import {storedQuery} from '../../../stores'

  export let swimlane = null

  let formatEstimation = (value) => (value / 60.0).toFixed(1) + "h"
</script>

<div class="table-row aggregated-swimlane">
  {#each swimlane as item}
      <div class="data-column"
           style="background: {getProjectCountColorCode(item.projects.length)}; --data-size: {item.duration}">
          <span class="workload-value ">{item.projects.length}</span>
      </div>
  {/each}
</div>


<style>
    .table-row {
        display: flex;
        height: var(--table-extended-row-height);
        padding: var(--table-extended-row-v-padding);
        align-items: center;
    }

    .data-column {
        width: calc(var(--table-row-width) * var(--data-size));
        min-width: calc(var(--table-row-width) * var(--data-size));
        max-width: calc(var(--table-row-width) * var(--data-size));
        display: flex;
        height: 100%;
        justify-content: center;
        flex-direction: column;
        position: relative;
        text-align: center;
    }

    .data-column + .data-column {
        border-left: var(--table-item-separator);
    }
</style>