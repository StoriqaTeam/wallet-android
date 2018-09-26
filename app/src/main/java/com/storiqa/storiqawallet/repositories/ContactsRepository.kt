package com.storiqa.storiqawallet.repositories

import android.provider.ContactsContract
import com.storiqa.storiqawallet.StoriqaApp
import com.storiqa.storiqawallet.objects.Contact
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class ContactsRepository {

    private  var storedContactsObservable : Observable<Array<Contact>>? = null

    private fun fetchContactsFromPhone(): Array<Contact> {
        val contentResolver = StoriqaApp.context.contentResolver
        val cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)

        val contactList = ArrayList<Contact>()

        if ((if (cursor != null) cursor.count else 0) > 0) {
            while (cursor != null && cursor.moveToNext()) {
                val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val photo = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI))

                var phoneNo = ""
                if (cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    val pCur = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", arrayOf<String>(id), null)

                    while (pCur.moveToNext()) {
                        phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    }
                    pCur.close()
                }

                if(phoneNo.isNotEmpty()) {
                    contactList.add(Contact(id, name ?: "", phoneNo ?: "", photo ?: ""))
                }
            }
        }
        if (cursor != null) {
            cursor.close()
        }

        return contactList.toTypedArray()
    }

    fun getContacts(): Observable<Array<Contact>> {
        if(storedContactsObservable != null) {
             return storedContactsObservable!!
        } else {
            storedContactsObservable = Observable.create<Array<Contact>> {emitter ->
                emitter.onNext(fetchContactsFromPhone())
                emitter.onComplete()
            }.flatMap {contacts ->
                WalletsRepository().getWallets(contacts.map { it.phone }.toTypedArray()).map { phoneToWallet ->
                    for (contact in contacts) {
                        contact.wallet = phoneToWallet[contact.phone] ?: ""
                    }
                    contacts
                }
            }
            return storedContactsObservable!!
        }
    }
}