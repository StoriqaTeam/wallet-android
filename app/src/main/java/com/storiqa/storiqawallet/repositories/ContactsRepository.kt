package com.storiqa.storiqawallet.repositories

import android.provider.ContactsContract
import com.storiqa.storiqawallet.StoriqaApp
import com.storiqa.storiqawallet.objects.Contact


class ContactsRepository {

    fun getContactList(): Array<Contact> {
        val cr = StoriqaApp.context.getContentResolver()
        val cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)

        val contactList = ArrayList<Contact>()

        if ((if (cur != null) cur.getCount() else 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                val id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID))
                val name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                var phoneNo = ""
                var photo = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI)) ?: ""

                if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    val pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", arrayOf<String>(id), null)

                    while (pCur.moveToNext()) {
                        phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    }
                    pCur.close()
                }

                contactList.add(Contact(name, phoneNo, photo))
            }
        }
        if (cur != null) {
            cur.close()
        }

        return contactList.toTypedArray()

    }


}