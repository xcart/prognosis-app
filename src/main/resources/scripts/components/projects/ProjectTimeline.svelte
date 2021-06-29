<script>
  import ManagerList from "./timeline/ManagerList.svelte"
  import CalendarView from "../table/CalendarView.svelte"
  import AggregatedProjectSwimlane from "./timeline/AggregatedProjectSwimlane.svelte"
  import ProjectSwimlane from "./timeline/ProjectSwimlane.svelte"
  import RowContainer from "../table/RowContainer.svelte"

  export let groups = []
  export let duration = null
</script>

<div class="table-body project-timeline">
    <ManagerList groups={groups}/>
    <CalendarView {duration}>
        {#each groups as group}
            <AggregatedProjectSwimlane swimlane={group.spans}/>
            {#each group.projects as project}
                <ProjectSwimlane project={project}/>
            {/each}
        {/each}
    </CalendarView>
</div>

<style>
    .project-timeline {
        display: flex;
    }
</style>