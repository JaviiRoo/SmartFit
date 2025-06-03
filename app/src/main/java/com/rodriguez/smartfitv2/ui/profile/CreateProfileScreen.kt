package com.rodriguez.smartfitv2.ui.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.rodriguez.smartfitv2.data.model.Gender
import com.rodriguez.smartfitv2.data.model.Profile
import com.rodriguez.smartfitv2.data.repository.ProfileRepository
import com.rodriguez.smartfitv2.navigation.Routes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateProfileScreen(
    navController: NavHostController,
    profileRepository: ProfileRepository,
    profileId: Int? = null
) {
    val primaryColor = Color(0xFFE91E63)
    val surfaceColor = Color(0xFF1E1E1E)
    val backgroundColor = Color(0xFF121212)
    val textColor = Color.White

    var name by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf(Gender.HOMBRE) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var isLoaded by remember { mutableStateOf(false) }
    val isEditMode = profileId != null
    val scope = rememberCoroutineScope()

    val picker = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? -> imageUri = uri }

    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(300)
        visible = true
    }

    LaunchedEffect(profileId) {
        if (isEditMode && !isLoaded) {
            profileRepository.getAllProfiles().find { it.id == profileId?.toLong() }?.let {
                name = it.name
                gender = it.gender
                imageUri = it.image?.let(Uri::parse)
            }
            isLoaded = true
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(backgroundColor, surfaceColor)))
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .background(surfaceColor.copy(alpha = 0.7f))
                        .border(2.dp, primaryColor.copy(alpha = 0.5f), CircleShape)
                        .clickable { picker.launch("image/*") },
                    contentAlignment = Alignment.Center
                ) {
                    if (imageUri != null) {
                        Image(
                            painter = rememberAsyncImagePainter(imageUri),
                            contentDescription = "Foto de perfil",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Avatar predeterminado",
                            tint = primaryColor.copy(alpha = 0.5f),
                            modifier = Modifier.size(64.dp)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .offset((-8).dp, (-8).dp)
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(primaryColor)
                            .border(2.dp, Color.White, CircleShape)
                            .clickable { picker.launch("image/*") }
                            .padding(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PhotoCamera,
                            contentDescription = "Cambiar foto",
                            tint = Color.White,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nombre", color = textColor.copy(alpha = 0.8f)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = primaryColor,
                        unfocusedBorderColor = textColor.copy(alpha = 0.6f),
                        cursorColor = primaryColor,
                        focusedLabelColor = primaryColor,
                        unfocusedLabelColor = textColor.copy(alpha = 0.6f),
                        focusedTextColor = textColor,
                        unfocusedTextColor = textColor
                    ),
                    textStyle = LocalTextStyle.current.copy(fontSize = 18.sp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Género",
                    color = textColor.copy(alpha = 0.8f),
                    fontSize = 16.sp,
                    modifier = Modifier.align(Alignment.Start)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Gender.values().forEach { g ->
                        val isSelected = gender == g
                        Card(
                            onClick = { gender = g },
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) primaryColor.copy(alpha = 0.2f)
                                else surfaceColor.copy(alpha = 0.5f)
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 6.dp
                            ),
                            border = BorderStroke(
                                width = 1.dp,
                                color = if (isSelected) primaryColor else Color.Transparent
                            )
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = g.name,
                                    color = if (isSelected) primaryColor else textColor.copy(alpha = 0.8f),
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        scope.launch {
                            val profile = Profile(
                                id = profileId?.toLong() ?: 0L,
                                name = name,
                                gender = gender,
                                image = imageUri?.toString()
                            )
                            if (isEditMode) {
                                profileRepository.updateProfile(profile)
                                withContext(Dispatchers.Main) {
                                    navController.navigate(Routes.PROFILE_SELECTOR) {
                                        popUpTo(Routes.PROFILE_SELECTOR) { inclusive = true }
                                    }
                                }
                            } else {
                                val newId = profileRepository.insertProfile(profile)
                                profileRepository.setSelectedProfile(newId)
                                withContext(Dispatchers.Main) {
                                    navController.navigate(
                                        Routes.HOME_WITH_ARG.replace(
                                            "{profileId}",
                                            newId.toInt().toString()
                                        )
                                    ) {
                                        popUpTo(Routes.PROFILE_SELECTOR) { inclusive = true }
                                    }
                                }
                            }
                        }
                    },
                    enabled = name.isNotBlank(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primaryColor,
                        disabledContainerColor = primaryColor.copy(alpha = 0.3f)
                    )
                ) {
                    Text(
                        text = if (isEditMode) "ACTUALIZAR PERFIL" else "CREAR PERFIL",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        letterSpacing = 1.sp
                    )
                }
            }
        }
    }
}
