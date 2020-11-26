<script>
  export let id = null
  export let stats = null

  const formatWorkload = (value) => (value / 60.0).toFixed(1) + "h"

  const getOverdueMinutes = (stats) => {
    let stat = stats.find(it => it.key === "OverdueEstimation")
    return stat ? stat.value : 0
  }
</script>

<div class="user-summary-tooltip" id="{id}">
    <span class="overdue-value">Overdue hours: {formatWorkload(getOverdueMinutes(stats))}</span>
</div>