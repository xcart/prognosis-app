<script>
  import Header from "./Header.svelte";
  import PageContainer from "./PageContainer.svelte";
  import ErrorHandler from "./components/ErrorHandler.svelte";

  export let url = "";
</script>

<section class="page">
  <ErrorHandler />
  <Header {url} />
  <PageContainer {url} />
</section>

<style>
  .page {
    font-size: 16px;
  }
</style>