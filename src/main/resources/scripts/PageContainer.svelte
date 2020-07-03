<script>
  import { Router, Route } from "svelte-routing";
  import Workload from "./pages/Workload.svelte";
  import About from "./pages/About.svelte";

  export let url = "";
  export let state = null;
</script>

<Router {url}>
  <section class="page-container">
    <Route path="/">
      <Workload {state} />
    </Route>
    <Route path="/about">
      <About {state} />
    </Route>
  </section>
</Router>
