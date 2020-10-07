<script>
  import ManagerList from "./timeline/ManagerList.svelte"
  import ProjectSwimlanes from "./timeline/ProjectSwimlanes.svelte"

  export let groups = []
  export let duration = null
</script>

<div class="table-body project-timeline">
    <ManagerList groups={groups}/>
    <ProjectSwimlanes groups={groups} duration={duration}/>
</div>

<style>
    .project-timeline {
        display: flex;
    }
</style>