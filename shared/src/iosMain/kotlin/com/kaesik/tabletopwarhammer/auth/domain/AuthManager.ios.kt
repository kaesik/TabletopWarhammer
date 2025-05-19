@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.kaesik.tabletopwarhammer.auth.domain

import com.kaesik.tabletopwarhammer.core.domain.util.DataError
import com.kaesik.tabletopwarhammer.core.domain.util.Resource
import kotlinx.coroutines.flow.Flow

actual class AuthManager {
    actual fun loginGoogleUser(): Flow<Resource<Unit, DataError.Network>> {
        TODO("Not yet implemented")
    }
}
