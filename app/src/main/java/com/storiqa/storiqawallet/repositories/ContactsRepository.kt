package com.storiqa.storiqawallet.repositories

import android.provider.ContactsContract
import com.storiqa.storiqawallet.StoriqaApp
import com.storiqa.storiqawallet.objects.Contact


class ContactsRepository {

    fun getContactList(): Array<Contact> {
        val contentResolver = StoriqaApp.context.contentResolver
        val cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)

        val contactList = ArrayList<Contact>()

        if ((if (cursor != null) cursor.getCount() else 0) > 0) {
            while (cursor != null && cursor.moveToNext()) {
                val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                var phoneNo = ""
                val photo = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI))

                if (cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    val pCur = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", arrayOf<String>(id), null)

                    while (pCur.moveToNext()) {
                        phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    }
                    pCur.close()
                }

                contactList.add(Contact(name?:"", phoneNo?:"", photo?:""))
            }
        }
        if (cursor != null) {
            cursor.close()
        }

        return contactList.toTypedArray()

    }


}