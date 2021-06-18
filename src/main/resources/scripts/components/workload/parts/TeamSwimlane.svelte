<script>
  import RowContainer from "../../table/RowContainer.svelte"
</script>

<RowContainer className="team-swimlane">
  <div class="full-column">
      <span></span>
  </div>
</RowContainer>

<style>
    .full-column {
        width: 100%;
        display: flex;
        height: 100%;
        justify-content: center;
        flex-direction: column;
        position: relative;
        text-align: left;
    }
</style>