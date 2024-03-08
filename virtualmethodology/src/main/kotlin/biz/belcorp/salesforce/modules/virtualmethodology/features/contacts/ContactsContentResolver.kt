package biz.belcorp.salesforce.modules.virtualmethodology.features.contacts

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.modules.virtualmethodology.features.contacts.model.ListContacts

class ContactsContentResolver(private val context: Context) {

    fun getAllContacts(): List<ListContacts> {
        val contacts = arrayListOf<ListContacts>()
        val contactsData = getContactsData()
        if (!hasElements(contactsData.size)) {
            return emptyList()
        } else contactsData.forEach { contacts.addAll(getContactsWithPhones(it)) }
        return contacts
    }

    private fun hasElements(number: Int) = number > Constant.NUMERO_CERO

    private fun getColumnData(cursor: Cursor, field: String): String = with(cursor) {
        getString(getColumnIndex(field)).orEmpty()
    }

    private fun getCursor(
        contentResolver: ContentResolver,
        uri: Uri,
        selection: String? = null,
        args: Array<String>? = null
    ): Cursor? {
        return contentResolver.query(uri, null, selection, args, null)
    }

    private fun getContactsData(): List<ContactData> {
        val cursor = getCursor(context.contentResolver, ContactsContract.Contacts.CONTENT_URI)
            ?: return emptyList()
        val contacts = arrayListOf<ContactData>()
        if (hasElements(cursor.count)) {
            while (cursor.moveToNext()) {
                val id = getColumnData(cursor, ContactsContract.Contacts._ID)
                val name = getColumnData(cursor, ContactsContract.Contacts.DISPLAY_NAME)
                val hasPhone =
                    getColumnData(cursor, ContactsContract.Contacts.HAS_PHONE_NUMBER).toInt()

                contacts.add(ContactData(id, name, hasElements(hasPhone)))
            }
        }
        cursor.close()
        return contacts
    }

    private fun getContactsWithPhones(contact: ContactData): List<ListContacts> {
        val cursor = getCursor(
            context.contentResolver,
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
            arrayOf(contact.id)
        ) ?: return emptyList()
        val contacts = arrayListOf<ListContacts>()
        if (hasElements(cursor.count)) {
            while (cursor.moveToNext()) {
                val phone =
                    getColumnData(cursor, ContactsContract.CommonDataKinds.Phone.NUMBER)
                contacts.add(ListContacts(contact.id.toLong(), false, phone, contact.name))
            }
        }
        cursor.close()
        return contacts
    }

    data class ContactData(val id: String, val name: String, val hasPhone: Boolean)

}




