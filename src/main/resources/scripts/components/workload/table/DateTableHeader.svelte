<script>
    import getDates from "../../../util/dates.js"
    export let duration = 30

    let dates = getDates(new Date(), new Date().addDays(duration - 1))
</script>

<div class="table-header">
  {#each dates as date}
      <div class="date-column">
          <span>{date.getUTCDate()}</span>
      </div>
  {/each}
</div>