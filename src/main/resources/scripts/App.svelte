<script>
  import Header from "./Header.svelte";
  import PageContainer from "./PageContainer.svelte";
  export let url = "";
</script>

<section class="page">
  <Header {url} />
  <PageContainer {url} />
</section>

<style>
  .page {
    font-size: 16px;
  }
</style>