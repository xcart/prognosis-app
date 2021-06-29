<script>
  import {createEventDispatcher} from 'svelte';
  import interact from "interactjs"
  import RowContainer from "../table/RowContainer.svelte"

  const dispatch = createEventDispatcher();

  export let dragAllowed = false
  export let key = null

  let dragStartPosition = null
  let dragStarted = false
  let translate

  const DAILY_STEP_SIZE = 32
  const position = {x: 0, y: 0}

  $: {
    if (dragAllowed) {
      translate = position
    } else {
      translate = {x: 0, y: 0}
    }
  }

  function resetPosition() {
    position.x = 0
    position.y = 0
  }

  let interactable = interact("#draggable-" + key)
    .draggable({
      lockAxis: 'x',
      modifiers: [
        interact.modifiers.snap({
          targets: [
            interact.snappers.grid({
              x: DAILY_STEP_SIZE,
              y: 40,
              offset: {x: -8, y: 0}
            })
          ],
          relativePoints: [
            {x: 0, y: 0}   // snap relative to the element's top-left,
          ]
        }),
        interact.modifiers.restrictRect({ // restrict movement to parent rectangle (rowcontainer)
          restriction: 'parent'
        })
      ],
      listeners: {
        start(event) {
          dragStarted = true
          dragStartPosition = {
            x: position.x,
            y: position.y
          }
        },
        move(event) {
          position.x += event.dx
        },
        end(event) {
          dragStarted = false
          dispatch('dragend', {
            key: key,
            delta: {
              x: position.x - dragStartPosition.x,
              y: position.y - dragStartPosition.y,
              stepX: (position.x - dragStartPosition.x) / DAILY_STEP_SIZE
            },
            resetPosition: resetPosition
          })
          dragStartPosition = null
        }
      }
    });

  $: {
    if (dragAllowed) {
      interactable.draggable(true)
    } else {
      interactable.draggable(false)
    }
  }
</script>

<div class="draggable-wrapper" id="draggable-{key}" class:dragged={dragStarted}
     style="transform: translate({translate.x}px, {translate.y}px);">
  <RowContainer className="draggable-content">
    <slot></slot>
  </RowContainer>
</div>

<style>
    .draggable-wrapper {
        display: inline-block;
        position: relative;
    }

    :global(.draggable-content) {
        transition: opacity .2s, box-shadow .2s;
    }

    .draggable-wrapper.dragged > :global(.draggable-content) {
        box-shadow: 0 0 0 0.15rem rgba(0, 123, 255, .8);
        border-radius: .2rem;
        opacity: 0.5;
    }
</style>