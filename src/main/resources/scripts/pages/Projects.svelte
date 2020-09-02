<script>
    import ProjectList from "../components/projects/ProjectList.svelte"
    import ProjectInfo from "../components/projects/ProjectInfo.svelte"
    import {Container} from "sveltestrap"
    import {state, storedQuery} from '../stores'
    import {onMount} from 'svelte';
    import {loadProjectsReport} from "../actions";

    export let user = null
    let projects = [],
        query = null;

    function isReady(state) {
        return state.report.type === 'Projects'
    }

    $: {
        if (isReady($state)) {
            query = $state.query
            projects = $state.report.projects
        } else {
            query = $storedQuery
        }
    }

    onMount(() => {
        if (!isReady($state)) {
            loadProjectsReport($storedQuery)
        } else {
            storedQuery.set($state.query)
        }
    })
</script>

<section class="page">
<!--    <div class="projects-list">-->
<!--    </div>-->
    <div class="projects-table">
        {#if projects.length > 0}
            <ProjectList projects={projects}/>
            <ProjectInfo projects={projects}/>
        {:else if isReady($state)}
            <div class="container">
                <p class="lead">Alas, the list is empty :(</p>
            </div>
        {/if}
    </div>
</section>

<style>
    .projects-table {
        display: flex;
    }
</style>
