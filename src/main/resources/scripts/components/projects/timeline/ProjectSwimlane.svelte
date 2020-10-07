<script>
  import {getContinuousColorCode} from "../../../util/colorCode"
  import {storedQuery} from '../../../stores'

  export let project = null

  let formatEstimation = (value) => (value / 60.0).toFixed(1) + "h"
  let getClientLink = (client) => {
    return "https://xcart.myjetbrains.com/youtrack/issues?q="
      + encodeURIComponent($storedQuery)
      + encodeURIComponent(" Client: {" + client + "}")
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

<div class="table-row user-swimlane">
    {#if project.offset > 0}
        <div class="data-column offset"
             style="--data-size: {project.offset}"></div>
    {/if}
    <div class="data-column"
         style="--data-size: {getSize(project)};">
        <a class="project-name" href={getClientLink(project.client)}
           target="_blank">
            {formatProjectName(project)}
        </a>
    </div>
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

    .data-column .project-name {
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
    }
</style>