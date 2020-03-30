package com.vivcom.data.repository

class RegionRepository(
    private val locationDataSource: LocationDataSource,
    private val permissionChecker: PermissionChecker

) {
    companion object {
        private const val DEFAULT_REGION = "US"
    }

    suspend fun findLastRegion(): String {
        return if (permissionChecker.check(PermissionChecker.Permission.COARSE_LOCATION)) {
            locationDataSource.findLastRegion() ?: DEFAULT_REGION
        } else
            DEFAULT_REGION
    }

}


interface LocationDataSource {
    suspend fun findLastRegion(): String?
}

interface PermissionChecker {
    enum class Permission { COARSE_LOCATION }

    suspend fun check(permission: Permission): Boolean
}

