<script>
  import {getContinuousColorCode} from "../../../util/colorCode"
  import {Link} from "svelte-routing";
  import {storedQuery} from '../../../stores'

  export let project = null

  let formatEstimation = (value) => (value / 60.0).toFixed(1) + "h"

  let buildProjectUrl = (client) => {
    return "/projects/" + encodeURIComponent(client) + "?query=" + encodeURIComponent($storedQuery)
  }

  let formatProjectName = (project) => project.client + " " + formatEstimation(project.estimation)
  let getBackgroundCode = (project) => getContinuousColorCode(project.estimation / project.duration)
  let getSize = (project) => {
    if (project.offset >= 0) {
      return project.duration
    }
    return project.duration + project.offset
  }
</script>

<div class="table-row">
    {#if project.offset > 0}
        <div class="data-column offset"
             style="--data-size: {project.offset}"></div>
    {/if}
    <div class="data-column"
         style="--data-size: {getSize(project)};">
        <Link to="{buildProjectUrl(project.client)}">{formatProjectName(project)}</Link>
    </div>
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
        background: #ddddddbb;
    }

    .data-column.offset {
        visibility: hidden;
    }

    .data-column :global(a) {
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
    }
</style>