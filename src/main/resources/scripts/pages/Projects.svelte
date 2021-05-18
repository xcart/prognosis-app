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
            // query = $state.query
            duration = $state.report.duration
            groups = $state.report.groups
        } else {
            // query = $storedQuery
        }
    }

    onMount(() => {
        if (!isReady($state)) {
            loadProjectsReport()
            // loadProjectsReport($storedQuery)
        } else {
            // storedQuery.set($state.query)
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
