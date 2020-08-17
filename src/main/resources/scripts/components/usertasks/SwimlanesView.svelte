<script>
    import Dates from "../table/Dates.svelte"
    import TaskSwimlane from "./parts/TaskSwimlane.svelte"

    export let tasks = null
    export let duration = null
</script>

<div class="swimlanes-section">
    <div class="table-header">
        <Dates duration={duration}/>
    </div>
    <div class="table-body">
      {#each tasks as task}
          <TaskSwimlane swimlane={task.swimlane} />
      {/each}
    </div>
</div>

<style>
    .table-header {
        border-bottom: var(--table-border);
        margin-bottom: var(--table-line-margin);
        width: max-content;
    }

    .table-body {
        width: max-content;
    }

    .swimlanes-section {
        max-width: 90vw;
        overflow: scroll;
        overflow-y: hidden;
    }
</style>