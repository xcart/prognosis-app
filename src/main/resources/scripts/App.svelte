<script>
  import Header from "./Header.svelte";
  import PageContainer from "./PageContainer.svelte";
  export let url = "";
  export let state = null;
</script>

<svelte:head>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</svelte:head>

<section class="page">
  <Header {url} />
  <PageContainer {url} {state} />
</section>

<style>
  .page {
    font-family: sans-serif;
    font-size: 16px;
  }
</style>