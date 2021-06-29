<script>
  import {getProjectCountColorCode} from "../../../util/colorCode"
  import {storedQuery} from '../../../stores'
  import RowContainer from "../../table/RowContainer.svelte"

  export let swimlane = null

  let formatEstimation = (value) => (value / 60.0).toFixed(1) + "h"
</script>

<RowContainer class="aggregated-project-swimlane">
  {#each swimlane as item}
      <div class="data-column"
           style="background: {getProjectCountColorCode(item.projects.length)}; --data-size: {item.duration}">
          <span class="workload-value ">{item.projects.length}</span>
      </div>
  {/each}
</RowContainer>


<style>
    .data-column {
        width: calc(var(--table-cell-width) * var(--data-size));
        min-width: calc(var(--table-cell-width) * var(--data-size));
        max-width: calc(var(--table-cell-width) * var(--data-size));
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