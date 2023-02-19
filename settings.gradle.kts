
rootProject.name = "SharkBackpack"
include("project:core")
include("project:common")
include("project:client")
include("internal")
include("client")
include("cloud")
include("project:client-enable")
findProject(":project:client-enable")?.name = "client-enable"
