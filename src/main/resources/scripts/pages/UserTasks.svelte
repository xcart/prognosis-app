<script>
  import TaskList from "../components/usertasks/TaskList.svelte"
  import MoveIcon from "bootstrap-icons/icons/arrows-move.svg"
  import {state, storedQuery, movedIssues} from '../stores'
  import {onMount} from 'svelte'
  import {loadUsertasksReport, getRescheduledSwimlane} from "../actions"
  import {applyMoveChanges} from "../util/moveTools"
  import CalendarView from "../components/table/CalendarView.svelte"
  import NoteBlock from "../components/block/NoteBlock.svelte"
  import DraggableBlock from "../components/block/DraggableBlock.svelte"
  import SingleIssueBlock from "../components/block/SingleIssueBlock.svelte"
  import OffsetBlock from "../components/block/OffsetBlock.svelte"
  import RowContainer from "../components/table/RowContainer.svelte"

  export let login = null

  let displayedTasks = []
  let tasks = []
  let duration = null
  let user = null
  let query = null
  let showTestingPhase = localStorage.getItem("usertasks.showTestingPhase") !== null
    ? JSON.parse(localStorage.getItem("usertasks.showTestingPhase"))
    : true
  let moveEnabled = false

  const MS_PER_DAY = 1000 * 60 * 60 * 24;

  function isReady(state) {
    return state.report.type === 'Usertasks' && state.report.login === login
  }

  function toggleMoveEnabled() {
    moveEnabled = !moveEnabled
    return false
  }

  function daysAfter(date) {
    let now = new Date()
    let due = new Date(date)
    let start = Date.UTC(due.getFullYear(), due.getMonth(), due.getDate());
    let end = Date.UTC(now.getFullYear(), now.getMonth(), now.getDate());
    return Math.floor((end - start) / MS_PER_DAY);
  }

  $: if (showTestingPhase !== null) {
    localStorage.setItem("usertasks.showTestingPhase", JSON.stringify(showTestingPhase))
  }

  $: {
    if (moveEnabled === true && Object.keys($movedIssues).length > 0) {
      displayedTasks = applyMoveChanges(tasks, $movedIssues)
    } else {
      displayedTasks = tasks
    }
  }

  $: {
    if (isReady($state)) {
      query = $state.query
      tasks = $state.report.tasks
      user = $state.report.user
      duration = $state.report.duration
    } else {
      query = $storedQuery
    }
  }

  onMount(() => {
    if (!isReady($state)) {
      loadUsertasksReport(login, $storedQuery)
    } else {
      storedQuery.set($state.query)
    }
  })
</script>

<section class="page">
    <div class="table-header container">
        <div></div>
        <form class="ml-auto page-controls title-aside">
            <div class="move-tools-button">
                <button type="button" class="btn {moveEnabled ? 'btn-secondary active' : 'btn-light'}"
                        on:click|preventDefault={toggleMoveEnabled}>
                    <MoveIcon width="16" height="16"/>
                    <span>Move tasks</span>
                </button>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="checkbox" bind:checked="{showTestingPhase}" value=""
                       id="showTestingPhase">
                <label class="form-check-label" for="showTestingPhase">
                    Show testing phase
                </label>
            </div>
        </form>
    </div>

    <div class="usertasks-table">
        {#if displayedTasks.length > 0}
            <TaskList tasks={displayedTasks} {user}/>
            <CalendarView {duration}>
                {#each displayedTasks as task}
                    <RowContainer>
                        {#if task.offset > 0}
                            <OffsetBlock amount={task.offset}/>
                        {/if}
                        <DraggableBlock dragAllowed={moveEnabled} key={task.issue.id}>
                            <SingleIssueBlock swimlane={task.swimlane} showTestingPhase={showTestingPhase}>
                                {#if task.overdue}
                                    <NoteBlock
                                            reason="Over due date by {daysAfter(task.endDate)} days ({task.endDate})"
                                            type="danger"/>
                                {:else if task.missedVerification}
                                    <NoteBlock
                                            reason="Missed verification date by {daysAfter(task.verificationDate)} days"/>
                                {/if}
                            </SingleIssueBlock>
                        </DraggableBlock>
                    </RowContainer>
                {/each}
            </CalendarView>
        {:else if isReady($state)}
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

    .table-header {
        display: flex;
        position: relative;
    }

    .page-controls {
        display: flex;
        align-items: center;
    }

    .page-controls > :global(* + *) {
        margin-left: 1rem;
    }

    .title-aside {
        position: absolute;
        top: -70px;
        right: 15px;
    }
</style>
