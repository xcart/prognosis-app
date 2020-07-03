<script>
  import { Router, Link } from "svelte-routing";
  export let url = "";
</script>

<header>
  <Router {url}>
    <nav>
      <Link to="/">Workload</Link>
      <Link to="about">About</Link>
    </nav>
  </Router>
</header>
