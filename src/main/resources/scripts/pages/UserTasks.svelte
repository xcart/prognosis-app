<script>
  import TaskList from "../components/usertasks/TaskList.svelte"
  import MoveIcon from "bootstrap-icons/icons/arrows-move.svg"
  import CancelIcon from "bootstrap-icons/icons/trash.svg"
  import {state, storedQuery, storedLogin, movedIssues, canChangeIssues} from '../stores'
  import {onMount} from 'svelte'
  import {loadUsertasksReport, getRescheduledSwimlane, clearMovedIssues, persistMovedIssues} from "../actions"
  import {applyMoveChanges} from "../util/moveTools"
  import CalendarView from "../components/table/CalendarView.svelte"
  import NoteBlock from "../components/block/NoteBlock.svelte"
  import DraggableBlock from "../components/block/DraggableBlock.svelte"
  import SingleIssueBlock from "../components/block/SingleIssueBlock.svelte"
  import OffsetBlock from "../components/block/OffsetBlock.svelte"
  import RowContainer from "../components/table/RowContainer.svelte"
  import BlockingButton from "../components/controls/BlockingButton.svelte"

  export let login = null

  let displayedTasks = []
  let tasks = []
  let duration = null
  let user = null
  let showTestingPhase = localStorage.getItem("usertasks.showTestingPhase") !== null
    ? JSON.parse(localStorage.getItem("usertasks.showTestingPhase"))
    : true
  let moveEnabled = false
  let hasMoveChanges = false

  const MS_PER_DAY = 1000 * 60 * 60 * 24;

  function isReady(state) {
    return state.report.type === 'Usertasks' && state.report.login === login
  }

  function saveChanges(event) {
    if (!$canChangeIssues) {
      return
    }
    persistMovedIssues().then(() => {
      event.detail() // Unlock save button
      moveEnabled = false
    })
  }

  function toggleMoveEnabled() {
    if (!$canChangeIssues) {
      return
    }

    if (moveEnabled && hasMoveChanges) {
      if (!confirm("You have some unsaved changes. Do you really want to discard them?")) {
        return false
      }
      clearMovedIssues()
    }
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

  function onIssueMove(event) {
    if (typeof event.detail.key === "undefined") {
      return;
    }

    let shiftAmount = event.detail.delta.stepX
    if (typeof $movedIssues[event.detail.key] !== "undefined") {
      shiftAmount += $movedIssues[event.detail.key].shiftAmount
    }

    getRescheduledSwimlane(event.detail.key, shiftAmount).then(() => {
      event.detail.resetPosition()
    })
  }

  $: if (showTestingPhase !== null) {
    localStorage.setItem("usertasks.showTestingPhase", JSON.stringify(showTestingPhase))
  }

  $: {
    hasMoveChanges = Object.keys($movedIssues).length > 0
  }

  $: {
    if ($canChangeIssues && moveEnabled && hasMoveChanges) {
      displayedTasks = applyMoveChanges(tasks, $movedIssues)
    } else {
      displayedTasks = tasks
    }
  }

  $: {
    if (isReady($state)) {
      tasks = $state.report.tasks
      user = $state.report.user
      duration = $state.report.duration
    }
  }

  onMount(() => {
    if (!isReady($state)) {
      loadUsertasksReport(login, $storedQuery)
    } else {
      storedLogin.set(login)
    }
  })
</script>

<section class="page">
  <div class="table-header container">
    <div></div>
    <form class="ml-auto page-controls title-aside">
      {#if $canChangeIssues}
      <div class="move-tools-button" class:changed={hasMoveChanges}>
        <button type="button" class="btn {moveEnabled ? 'btn-secondary' : 'btn-light'}"
                on:click|preventDefault={toggleMoveEnabled}>
          {#if hasMoveChanges && moveEnabled}
            <CancelIcon width="16" height="16"/>
            <span>Cancel</span>
          {:else}
            <MoveIcon width="16" height="16"/>
            <span>Move tasks</span>
          {/if}
        </button>
        {#if hasMoveChanges && moveEnabled}
          <BlockingButton on:click={saveChanges}>
            <span>Save changes</span>
          </BlockingButton>
        {/if}
      </div>
      {/if}
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
            <DraggableBlock dragAllowed={moveEnabled && $canChangeIssues && !task.overdue} key={task.issue.id} on:dragend={onIssueMove}>
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
