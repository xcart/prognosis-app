<script>
  import interact from "interactjs"
  import RowContainer from "../table/RowContainer.svelte"

  export let dragAllowed = false
  export let key = null
  let dragStarted = false

  const DAILY_STEP_SIZE = 32
  const position = {x: 0, y: 0}

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
          console.log(event.type)
        },
        move(event) {
          position.x += event.dx

          event.target.setAttribute("data-shift", position.x / DAILY_STEP_SIZE)
        },
        end(event) {
          dragStarted = false
          console.log(event)
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
         style="transform: translate({position.x}px, {position.y}px);">
<!--    <div class="drag-handle" class:hidden={!dragStarted}></div>-->
    <RowContainer className="draggable-content">
        <slot></slot>
    </RowContainer>
</div>

<style>
    .draggable-wrapper {
        display: inline-block;
        position: relative;
    }

    .drag-handle {
        position: static !important;
        width: 96px;
        height: 40px;
        border: 1px solid #000;
    }

    .draggable-wrapper.dragged > :global(.draggable-content) {
        /*position: absolute;*/
        /*top: 0;*/
        opacity: 0.3;
    }
</style>