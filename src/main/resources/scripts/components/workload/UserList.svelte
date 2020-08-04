<script>
    import {ListGroup, ListGroupItem} from 'sveltestrap';

    export let report = null
</script>
<ListGroup flush class="user-section">
    <ListGroupItem class="empty"></ListGroupItem>
  {#each report as reportLine}
      <ListGroupItem>
          <span>{reportLine.user.login}</span>
      </ListGroupItem>
  {/each}
</ListGroup>

<style>
    :global(.user-section) {
        border-right: 1px solid #ddd;
    }

    :global(.user-section .list-group-item) {
        width: 150px;
    }
</style>