<script>
    import TaskList from "../components/usertasks/TaskList.svelte"
    import SwimlanesView from "../components/usertasks/SwimlanesView.svelte"
    import {Container} from "sveltestrap"
    import {state, storedQuery} from '../stores'
    import {onMount} from 'svelte';
    import {loadUsertasksReport} from "../actions";

    export let user = null
    let tasks = [],
            duration = null,
            query = null;

    function isReady(state) {
        return state.report.type === 'Usertasks' && state.report.login === user
    }

    $: {
        if (isReady($state)) {
            query = $state.query
            tasks = $state.report.tasks
            duration = $state.report.duration
        } else {
            query = $storedQuery
        }
    }

    onMount(() => {
        if (!isReady($state)) {
            loadUsertasksReport(user, $storedQuery)
        } else {
            storedQuery.set($state.query)
        }
    })
</script>

<section class="page">
    <div class="usertasks-table">
        {#if tasks.length > 0}
            <TaskList tasks={tasks}/>
            <SwimlanesView tasks={tasks} duration={duration}/>
        {:else}
            <div class="container">
                <p class="lead">Alas, the list is empty :(</p>
            </div>
        {/if}
    </div>
</section>

<style>
    .usertasks-table {
        display: flex;
    }
</style>
