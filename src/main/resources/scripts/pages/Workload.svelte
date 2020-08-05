<script>
    import UserList from "../components/workload/UserList.svelte"
    import WorkloadTable from "../components/workload/WorkloadTable.svelte"

    export let state = null
    let report = Object.values(state.report)
    let filterReport = (report) => report.filter((line) => line.items.length > 0)
</script>

<section class="page">
    <div class="workload-table">
        <UserList report={filterReport(report)}/>
        <WorkloadTable report={filterReport(report)}/>
    </div>
</section>

<style>
    :root {
        --table-row-height: 2rem;
        --table-row-width: 2rem;
        --table-line-margin: .5rem;
        --table-border: 1px solid #ddd;
    }

    .workload-table {
        display: flex;
    }
</style>
