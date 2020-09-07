<script>
    import ProjectGrid from "../components/projects/ProjectGrid.svelte"
    import {Container} from "sveltestrap"
    import {state, storedQuery} from '../stores'
    import {onMount} from 'svelte';
    import {loadProjectsReport} from "../actions";

    export let user = null
    let groups = [],
        query = null;

    function isReady(state) {
        return state.report.type === 'Projects'
    }

    $: {
        if (isReady($state)) {
            query = $state.query
            groups = $state.report.groups
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
    <Container>
    <div class="projects-list">
        {#if groups.length > 0}
            <ProjectGrid groups={groups}/>
        {:else if isReady($state)}
            <div class="container">
                <p class="lead">Alas, the list is empty :(</p>
            </div>
        {/if}
    </div>
    </Container>
</section>

<style>
    .projects-list {
        display: flex;
    }
</style>
