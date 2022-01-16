<template>
  <div class="hello">
    <h1>ESA Course Web Application Template</h1>
    <danceClasses-table/>
    <danceStudios-table/>
    <dancers-table/>
  </div>
</template>

<script>
import DanceClassesTable from './DanceClassesTable.vue'
import DanceStudiosTable from "./DanceStudiosTable";
import DancersTable from "./DancersTable";
export default {
  components: {DancersTable, DanceStudiosTable, DanceClassesTable },
  name: 'HelloWorld',
  data() {
    return {
    }
  }
}
</script>

<style scoped>
</style>