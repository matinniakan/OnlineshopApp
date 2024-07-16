package com.example.onlineshopapp.modules

import com.example.onlineshopapp.api.customer.UserApi
import com.example.onlineshopapp.api.invoices.InvoiceApi
import com.example.onlineshopapp.api.invoices.TransactionApi
import com.example.onlineshopapp.api.products.ColorApi
import com.example.onlineshopapp.api.products.ProductApi
import com.example.onlineshopapp.api.products.ProductCategoryApi
import com.example.onlineshopapp.api.site.BlogApi
import com.example.onlineshopapp.api.site.ContentApi
import com.example.onlineshopapp.api.site.SliderApi
import com.example.onlineshopapp.repositories.customer.UserRepository
import com.example.onlineshopapp.repositories.invoices.InvoiceRepository
import com.example.onlineshopapp.repositories.invoices.TransactionRepository
import com.example.onlineshopapp.repositories.products.ColorRepository
import com.example.onlineshopapp.repositories.products.ProductCategoryRepository
import com.example.onlineshopapp.repositories.products.ProductRepository
import com.example.onlineshopapp.repositories.site.BlogRepository
import com.example.onlineshopapp.repositories.site.ContentRepository
import com.example.onlineshopapp.repositories.site.SliderRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUserRepository(api: UserApi) = UserRepository(api = api)

    @Provides
    @Singleton
    fun provideInvoiceRepository(api: InvoiceApi) = InvoiceRepository(api = api)

    @Provides
    @Singleton
    fun provideTransactionRepository(api: TransactionApi) = TransactionRepository(api = api)

    @Provides
    @Singleton
    fun provideColorRepository(api: ColorApi) = ColorRepository(api = api)

    @Provides
    @Singleton
    fun provideProductCategoryRepository(api: ProductCategoryApi) = ProductCategoryRepository(api = api)

    @Provides
    @Singleton
    fun provideProductRepository(api: ProductApi) = ProductRepository(api = api)

    @Provides
    @Singleton
    fun provideSliderRepository(api: SliderApi) = SliderRepository(api = api)

    @Provides
    @Singleton
    fun provideBlogRepository(api: BlogApi) = BlogRepository(api = api)

    @Provides
    @Singleton
    fun provideContentRepository(api: ContentApi) = ContentRepository(api = api)
}