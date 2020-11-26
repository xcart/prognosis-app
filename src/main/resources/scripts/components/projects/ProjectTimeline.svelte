<script>
  import ManagerList from "./timeline/ManagerList.svelte"
  import SwimlanesCalendar from "../table/SwimlanesCalendar.svelte"
  import AggregatedProjectSwimlane from "./timeline/AggregatedProjectSwimlane.svelte"
  import ProjectSwimlane from "./timeline/ProjectSwimlane.svelte"

  export let groups = []
  export let duration = null
</script>

<div class="table-body project-timeline">
    <ManagerList groups={groups}/>
    <SwimlanesCalendar {duration}>
      {#each groups as group}
          <AggregatedProjectSwimlane swimlane={group.spans} />
          {#each group.projects as project}
            <ProjectSwimlane project={project} />
          {/each}
      {/each}
    </SwimlanesCalendar>
</div>

<style>
    .project-timeline {
        display: flex;
    }
</style>