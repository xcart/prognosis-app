<script>
    import ProjectsView from "../components/projects/ProjectsView.svelte"
    import {state, storedQuery} from '../stores'
    import {onMount} from 'svelte';
    import {loadProjectsReport} from "../actions";

    let groups = [],
        duration = null,
        query = null;

    function isReady(state) {
        return state.report.type === 'Projects'
    }

    $: {
        if (isReady($state)) {
            duration = $state.report.duration
            groups = $state.report.groups
        }
    }

    onMount(() => {
        if (!isReady($state)) {
            loadProjectsReport()
        }
    })
</script>

<section class="page">
    <div class="projects-list">
        {#if groups.length > 0}
            <ProjectsView groups={groups} duration={duration}/>
        {:else if isReady($state)}
            <div class="container">
                <p class="lead">Alas, the list is empty :(</p>
            </div>
        {/if}
    </div>
</section>

<style>
    .projects-list {
        display: flex;
    }
</style>
