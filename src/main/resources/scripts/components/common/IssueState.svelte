<script>
  export let state = null

  const getShortState = (state) => {
    if (!state) {
      return ""
    }

    if (state === "Submitted") {
      return "Su"
    }
    if (state === "Scheduled") {
      return "Sc"
    }

    return state.replace(/[^A-Z]/g, "")
  }
</script>

<style>
    .issue-state {
        display: inline-block;
        text-align: center;
        width: 24px;
        height: 24px;
        border-radius: 3px;
        line-height: 20px;
        color: #fff;
    }

    .issue-state.Su {
        background-color: #D8F7F3;
        color: #45818E;
    }

    .issue-state.Sc {
        background-color: #25BEB2;
    }

    .issue-state.W {
        background-color: #FF7BC3;
    }

    .issue-state.N {
        background-color: #A6E0FC;
    }

    .issue-state.IP {
        background-color: #0070E4;
    }

    .issue-state.QA {
        background-color: #FF7123;
    }

    .issue-state.HD {
        background-color: #E30000;
    }

    .issue-state.QP {
        background-color: #8D5100;
    }

    .issue-state.OR {
        background-color: #553000;
    }

    .issue-state.C {
        background-color: #409600;
    }
</style>

<div class="issue-state {getShortState(state)}">
    <small>{getShortState(state)}</small>
</div>